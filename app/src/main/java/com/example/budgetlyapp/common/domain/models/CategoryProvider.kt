package com.example.budgetlyapp.common.domain.models

object CategoryProvider {
    val categories = listOf(
        TagModel(1, "tag_saving", "#F765A3", "ic_savings_category"),
        TagModel(2, "tag_home", "#7987FF", "ic_home_category"),
        TagModel(3, "tag_health", "#90BE6D", "ic_health_category"),
        TagModel(4, "tag_pet", "#F3722C", "ic_pet_category"),
        TagModel(5, "tag_transport", "#A155B9", "ic_transport_category"),
        TagModel(6, "tag_market", "#F8961E", "ic_market_category"),
        TagModel(7, "tag_sure", "#577590", "ic_sure_category"),
        TagModel(8, "tag_study", "#2D9CDB", "ic_study_category"),
        TagModel(9, "tag_subscription", "#43AA8B", "ic_subscription_category"),
        TagModel(10, "tag_other", "#F94144", "ic_other_category")
    )
}