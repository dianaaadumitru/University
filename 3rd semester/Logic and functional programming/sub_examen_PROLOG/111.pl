subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T],R):-
    subs(T,R).

getLen([],0).
getLen([_|T],R):-
    getLen(T,R1),
    R is R1+1.

getSum([],0).
getSum([H|T],R):-
    getSum(T,R1),
    R is R1+H.

checkL(L, N):-
    getLen(L,R),
    N =< R.


onesol(L,N,R):-
    subs(L,R),
    getLen(R,R1),
    N =< R1,
%    checkL(R, N),
    getSum(R,S),
    S mod 3 =:= 0.


allsol(L,N,R):-
    findall(RA, subs(L,RA),R).


