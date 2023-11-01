package kata;

sealed interface Event
    permits PlayerFlipsCard, PlayerSwapsCardWithDiscardPileEvent, PlayerTakesCardFromDeckEvent {}
