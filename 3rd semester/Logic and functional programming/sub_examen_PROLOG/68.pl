subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T], R):-
    subs(T,R).


getSum([],0).
getSum([H|T],S):-
    getSum(T,S1),
    S is S1+H.

onesol(L,R):-
    subs(L,R),
    getSum(R,S),
    S mod 2 =:= 0.

allsol(L,R):-
    findall(RA, onesol(L,RA),R).
