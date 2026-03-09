package com.travelplanner.service;

import com.travelplanner.dto.expense.CreateExpenseRequest;
import com.travelplanner.dto.expense.ExpenseResponse;
import com.travelplanner.dto.expense.ExpenseSplitResponse;
import com.travelplanner.dto.expense.TripExpenseSummaryResponse;
import com.travelplanner.dto.expense.UserBalanceResponse;
import com.travelplanner.exception.BadRequestException;
import com.travelplanner.exception.ResourceNotFoundException;
import com.travelplanner.model.Expense;
import com.travelplanner.model.ExpenseSplit;
import com.travelplanner.model.Trip;
import com.travelplanner.model.User;
import com.travelplanner.repository.ExpenseRepository;
import com.travelplanner.repository.ExpenseSplitRepository;
import com.travelplanner.repository.TripMemberRepository;
import com.travelplanner.repository.TripRepository;
import com.travelplanner.repository.UserRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripMemberRepository tripMemberRepository;

    @Transactional
    public ExpenseResponse create(CreateExpenseRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        if (request.getSplitAmongUserIds().isEmpty()) {
            throw new BadRequestException("Split members are required");
        }

        User paidBy = userRepository.findById(request.getPaidBy())
            .orElseThrow(() -> new ResourceNotFoundException("Payer user not found"));

        validateTripMembership(trip.getId(), request.getPaidBy());
        request.getSplitAmongUserIds().forEach(userId -> validateTripMembership(trip.getId(), userId));

        Expense expense = Expense.builder()
            .trip(trip)
            .paidBy(paidBy)
            .amount(request.getAmount())
            .description(request.getDescription().trim())
            .date(request.getDate())
            .build();

        Expense savedExpense = expenseRepository.save(expense);
        List<ExpenseSplit> splits = createSplits(savedExpense, request.getSplitAmongUserIds());

        return toResponse(savedExpense, splits);
    }

    @Transactional(readOnly = true)
    public TripExpenseSummaryResponse getByTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip not found");
        }

        List<Expense> expenses = expenseRepository.findByTripIdOrderByDateDescIdDesc(tripId);
        List<ExpenseResponse> expenseResponses = expenses.stream()
            .map(expense -> toResponse(expense, expenseSplitRepository.findByExpenseId(expense.getId())))
            .toList();

        List<UserBalanceResponse> balances = calculateBalances(expenses);

        return TripExpenseSummaryResponse.builder()
            .tripId(tripId)
            .expenses(expenseResponses)
            .balances(balances)
            .build();
    }

    private List<ExpenseSplit> createSplits(Expense expense, List<Long> splitUserIds) {
        Set<Long> uniqueUserIds = Set.copyOf(splitUserIds);
        BigDecimal splitCount = BigDecimal.valueOf(uniqueUserIds.size());
        BigDecimal share = expense.getAmount().divide(splitCount, 2, RoundingMode.HALF_UP);

        List<ExpenseSplit> splits = uniqueUserIds.stream()
            .map(userId -> {
                User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Split user not found: " + userId));
                return ExpenseSplit.builder()
                    .expense(expense)
                    .user(user)
                    .shareAmount(share)
                    .build();
            })
            .toList();

        return expenseSplitRepository.saveAll(splits);
    }

    private void validateTripMembership(Long tripId, Long userId) {
        if (!tripMemberRepository.existsByTripIdAndUserId(tripId, userId)) {
            throw new BadRequestException("User " + userId + " is not a member of trip " + tripId);
        }
    }

    private List<UserBalanceResponse> calculateBalances(List<Expense> expenses) {
        Map<Long, BigDecimal> balances = new HashMap<>();

        for (Expense expense : expenses) {
            balances.merge(expense.getPaidBy().getId(), expense.getAmount(), BigDecimal::add);

            List<ExpenseSplit> splits = expenseSplitRepository.findByExpenseId(expense.getId());
            for (ExpenseSplit split : splits) {
                balances.merge(split.getUser().getId(), split.getShareAmount().negate(), BigDecimal::add);
            }
        }

        return balances.entrySet().stream()
            .map(entry -> UserBalanceResponse.builder()
                .userId(entry.getKey())
                .balance(entry.getValue())
                .build())
            .toList();
    }

    private ExpenseResponse toResponse(Expense expense, List<ExpenseSplit> splits) {
        List<ExpenseSplitResponse> splitResponses = splits.stream()
            .map(split -> ExpenseSplitResponse.builder()
                .userId(split.getUser().getId())
                .shareAmount(split.getShareAmount())
                .build())
            .toList();

        return ExpenseResponse.builder()
            .id(expense.getId())
            .tripId(expense.getTrip().getId())
            .paidBy(expense.getPaidBy().getId())
            .amount(expense.getAmount())
            .description(expense.getDescription())
            .date(expense.getDate())
            .splits(splitResponses)
            .build();
    }
}
