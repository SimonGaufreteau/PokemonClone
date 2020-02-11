# Lexical-Adaptation documentation
## 1 - RunMove method
### 1.1 - Introduction
Each time a move is selected from the list of attacks, the game needs to update both the attacker and the attacked.
To assume this function, we are building a method in the LexicalAdaptation class to make a more flexible code that can be reused.


### 1.2 - What does it accept   
 
Here are all the syntax this method will accept as a correct Move description. Anything else will raise an Exception.

Note : Anything between {} represents a variable, an attribute or a specific Class.
 
1) (Empty String)
2) Always inflicts X HP
3) Charges on first turn
4) Confuses {target}
5) Damage occurs X turn later
6) Doubles in power each turn for X turns
7) Halves damage from {Type} attacks for X turns
8) High critical ratio
9) Hits 2-5 times in one turn
10) Hits twice in one turn
11) If {condition}, {action}
12) Ignores Accuracy and Evasiveness
13) Inflicts damage {value "of" / "equal to"} user's level
14) Makes it {Weather} for X turns
15) One-Hit-KO, if it hits
16) {target = Opponent} {action} (ex: cannot flee or switch)
17) Traps opponent, damaging them for 4-5 turns
18) User {action = attacks first / attacks for X-X turns (but then becomes {Status}) / faints / must recharge next turn / receives recoil damage / recovers {number or quantity} (inflicted on opponent) }
19) Weakens the power of {Type}-type moves
20) When hit by {type of attack} Attack, user strikes by with {multiplier}x power

--> Status / Stat effects :
NOTE : these description are additional, they can be found after any of the previous description (or none)
A) May {status effect} opponent
B) May cause flinching
C) Lowers {target} {Stat}
D) Raise {target} {Stats} / Raise {Stat} {target}
E) {Status} opponent
D) Puts (opponent to sleep) (Optionnal : in X turns)
E) Sharply (D / C)
F) May (C / D)


#### 1.2.1 - KeyWords : 

(Numbers) Always / Charges / Confuses / Damage / Doubles / Halves / High / Hits / Ignores / Inflicts
/ Makes (Not implemented yet) / One-Hit-KO / Traps

(Letters) Lowers / Raise / {Status} / Puts

(Others) May / Sharply


#### 1.2.1 - Implementation : 
Only the keywords will be implemented for now.