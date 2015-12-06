#! /usr/bin/env python
# -*- coding: utf-8 -*-
# Name: Akhil

'''
    ClassName:Deck
    Parameter:cards
    Method:shuffleCards applies faro shuffle algorithm
'''
class Deck(object):
    def __init__(self):
        self.cards = [
            'A♠', '2♠', '3♠', '4♠', '5♠', '6♠', '7♠', '8♠', '9♠', '10♠', 'J♠',
            'Q♠', 'K♠', 'A♦', '2♦', '3♦', '4♦', '5♦', '6♦', '7♦', '8♦', '9♦',
            '10♦', 'J♦', 'Q♦', 'K♦', 'K♣', 'Q♣', 'J♣','10♣', '9♣', '8♣', '7♣',
            '6♣', '5♣', '4♣', '3♣', '2♣', 'A♣', 'K♥', 'Q♥','J♥', '10♥', '9♥', 
            '8♥', '7♥', '6♥', '5♥', '4♥','3♥', '2♥', 'A♥']
    def __eq__(self, other):
        return self.cards == other.cards

    def shuffleCards(self):
        '''Shuffles the deck using a perfect faro shuffle.'''
        r = []
        for (a, b) in zip(self.cards[0:26], self.cards[26:]):
            r.append(a)
            r.append(b)
        self.cards = r

def check(original_deck,shuffled_deck):
    for i in range(1, 1000):
        shuffled_deck.shuffleCards()
        if shuffled_deck == original_deck:
            print("Deck is back in new-deck order after %s shuffles." % i)
            break

def main():
    original_deck = Deck()
    shuffled_deck = Deck()
    shuffled_deck.shuffleCards()
    for i in range(52):
        print(shuffled_deck.cards[i][0]+""+shuffled_deck.cards[i][1:]),


if __name__ == '__main__':
    main();
