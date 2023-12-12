package kata;

sealed interface Event
        permits PlayerFlipsCard, PlayerFlipsCardDuringGame, PlayerPutsCardOnDiscardPile, PlayerSwapsTakenCardWithCardAtPosition, PlayerTakesCardFromDeck, PlayerTakesCardFromDiscardPile {}
