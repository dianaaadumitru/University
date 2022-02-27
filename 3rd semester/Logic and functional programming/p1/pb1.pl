%Write a predicate to determine the lowest common multiple of a list formed from integer numbers

gcd(A,0,A).
gcd(0,A,A).
gcd(A,B,R):- A>B,
    gcd(B,A,R).
gcd(A,B,R):-
    M is B mod A,
    gcd(A,M,R).

lcm(A,B,R):-
    gcd(A,B,RES),
    R is A * B / RES.

lcmList([],0).
lcmList([H],H).
lcmList([H|T],R):-lcmList(T,R1),
    lcm(R1,H,R).

%Write a predicate to add a value v after 1-st, 2-nd, 4-th, 8-th, … element in a list.

insertPow([],_,_,_,[]).
insertPow([H|T],V,POS,INDEX,[H,V|R]):- POS=:=INDEX,
    NewPos is POS * 2,
    NewIndex is INDEX + 1,
    insertPow(T,V,NewPos,NewIndex,R).
insertPow([H|T],V,POS,INDEX,[H|R]):- POS=\=INDEX,
    NewIndex is INDEX + 1,
    insertPow(T,V,POS,NewIndex,R).

insertPos(L,V,R):-insertPow(L,V,1,1,R).
