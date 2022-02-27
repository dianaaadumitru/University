insert(E,L,[E|L]).
insert(E,[H|T], [H|R]):-
    insert(E,T,R).

perm([],[]).
perm([H|T],R):-
    perm(T,RT),
    insert(H,RT,R).

doList(N, L):-
  findall(Num, between(1, N, Num), L).

absdiff([]).
absdiff([_]).
absdiff([A,B|T]):-
    abs(A-B) >=2,
    absdiff([B|T]).

onesol(N,R):-
    doList(N,L),
    perm(L,R),
    absdiff(L).

allsol(N,R):-
    findall(RA,onesol(N,RA),R).
