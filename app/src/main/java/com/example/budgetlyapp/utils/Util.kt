package com.example.budgetlyapp.utils

class Util {
    companion object {
        enum class MoneyType {
            USD {
                override fun returnMoneyType() = "USD"
            },
            COP {
                override fun returnMoneyType() = "COP"
            },
            EUR {
                override fun returnMoneyType() = "EUR"
            };

            abstract fun returnMoneyType(): String
        }
    }
}