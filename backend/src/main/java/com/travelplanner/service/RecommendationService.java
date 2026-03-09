package com.travelplanner.service;

import com.travelplanner.dto.recommendation.RecommendationResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private static final Map<String, RecommendationResponse> CITY_DATA = Map.of(
        "paris", RecommendationResponse.builder()
            .destination("Paris")
            .attractions(List.of("Eiffel Tower", "Louvre Museum", "Seine River Cruise"))
            .restaurants(List.of("Le Jules Verne", "Septime", "Bouillon Pigalle"))
            .hotels(List.of("Hotel Lutetia", "Le Meurice", "Htel Fabric"))
            .build(),
        "bali", RecommendationResponse.builder()
            .destination("Bali")
            .attractions(List.of("Ubud Monkey Forest", "Tanah Lot Temple", "Tegallalang Rice Terraces"))
            .restaurants(List.of("Locavore", "Merah Putih", "Sisterfields"))
            .hotels(List.of("Alila Seminyak", "The Mulia", "Ayana Resort"))
            .build(),
        "goa", RecommendationResponse.builder()
            .destination("Goa")
            .attractions(List.of("Dudhsagar Falls", "Basilica of Bom Jesus", "Baga Beach"))
            .restaurants(List.of("Gunpowder", "Pousada by the Beach", "Mum's Kitchen"))
            .hotels(List.of("Taj Exotica", "W Goa", "Alila Diwa Goa"))
            .build()
    );

    public RecommendationResponse getRecommendations(String destination) {
        String key = destination == null ? "" : destination.trim().toLowerCase();
        return CITY_DATA.getOrDefault(key, RecommendationResponse.builder()
            .destination(destination)
            .attractions(List.of("City Walking Tour", "Main Museum", "Central Park"))
            .restaurants(List.of("Top-rated Local Bistro", "Popular Street Food Hub", "Farm-to-table Cafe"))
            .hotels(List.of("Central Business Hotel", "Boutique Stay", "Budget City Inn"))
            .build());
    }
}
