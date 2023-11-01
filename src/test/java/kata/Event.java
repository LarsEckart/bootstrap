package kata;

sealed interface Event
        permits PlayerFlipsCard, PlayerSwapsTakenCardWithCardAtPosition, PlayerTakesCardFromDeck, PlayerTakesCardFromDiscardPile {}
