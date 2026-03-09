package com.travelplanner.controller;

import com.travelplanner.dto.expense.CreateExpenseRequest;
import com.travelplanner.dto.expense.ExpenseResponse;
import com.travelplanner.dto.expense.TripExpenseSummaryResponse;
import com.travelplanner.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Expense management APIs")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(summary = "Add trip expense")
    public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody CreateExpenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(request));
    }

    @GetMapping("/{tripId}")
    @Operation(summary = "Get expenses and balances by trip")
    public ResponseEntity<TripExpenseSummaryResponse> getExpenses(@PathVariable Long tripId) {
        return ResponseEntity.ok(expenseService.getByTrip(tripId));
    }
}
