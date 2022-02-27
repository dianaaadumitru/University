lenList([],0).
lenList([_|T],R):-
    lenList(T,R1),
    R is R1+1.

sumElem([],0).
sumElem([H|T],R):-
    sumElem(T,R1),
    R is R1+H.

subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T],R):-
    subs(T,R).

checkSum(L):-
    sumElem(L,R),
    R mod 3 =:= 0.

checkLen(L,N):-
    lenList(L,R),
    R >= N.

onesol(L,N,R):-
    subs(L,R),
    checkSum(R),
    checkLen(R,N).

allsol(L,N,R):-
    findall(Ra,onesol(L,N,Ra),R).
