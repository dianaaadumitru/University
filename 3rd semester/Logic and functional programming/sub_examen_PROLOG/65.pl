%nrElements(l1l2..ln) =
%          0, n = 0
%          1 + nrEelemnts(l2...ln), otherwise

%nrEelemnts(i,o)
%nrEe=lements(L-list, R-integer(resulted length))


nrElements([], 0).
nrElements([_|T], R):-
    nrElements(T,R1),
    R is R1 + 1.

ratie([H1,H2|_],R):-
    R is H2-H1.

checkProgression([H1,H2],Rat, R):-
    R1 is H2-H1,
    R1 =:=  Rat,
    R is 1.
checkProgression([H1,H2|_], Rat, R):-
    R1 is H2 - H1,
    R1 \= Rat,
    R is 0.
checkProgression([H1,H2|T], Rat, R):-
    R1 is H2 - H1,
    R1 =:= Rat,
    checkProgression([H2|T], Rat, R2),
    R is R2.

checkOdd([]).
checkOdd([H|T]):-
    H mod 2 =:=1,
    checkOdd(T).

subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T], R):-
    subs(T,R).

onesol(L,K,R):-
    subs(L,R),
    nrElements(R,LEN),
    LEN =:= K,
    ratie(R,RATIE),
    checkProgression(R,RATIE,R2),
    R2 =:= 1,
    checkOdd(R).

allsol(L,K,R):-
    findall(RA,onesol(L,K,RA),R).
