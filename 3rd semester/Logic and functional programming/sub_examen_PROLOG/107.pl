comb([E|_],1,[E]).
comb([_|T],K,R):-
    comb(T,K,R).
comb([H|T],K,[H|R]):-
    K>1,
    K1 is K-1,
    comb(T,K1,R).

getSum([],0).
getSum([H|T],S):-
    getSum(T,S1),
    S is S1+H.

onestep(L,K,R):-
    comb(L,K,R),
    getSum(R,S),
    S mod 2 =:= 0.

allstep(L,K,R):-
    findall(RA, onestep(L,K,RA),R).
