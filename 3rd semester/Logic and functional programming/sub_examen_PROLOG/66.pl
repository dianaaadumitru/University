doList(N,L):-
    findall(Num, between(1,N,Num),L).

comb([E|_],1,[E]).
comb([_|T],K,R):-
    comb(T,K,R).
comb([H|T],K,[H|R]):-
    K>1,
    K1 is K-1,
    comb(T,K1,R).

absdiff([]).
absdiff([_]).
absdiff([A,B|T]):-
    abs(A-B) mod 2 =:= 0,
    absdiff([B|T]).


onesol(N,K,R):-
    doList(N,L),
    comb(L,K,R),
    absdiff(R).

allsol(N,K,R):-
    findall(RA, onesol(N,K,RA),R).
