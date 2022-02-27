%For a list a1... an with integer and distinct numbers, define a
% predicate to determine all subsets with sum of elements divisible with
% n


%subsets of a list
%subs(l1l2...ln) =
%        [], n = 0
%        l1 + subs(l2...ln), n > 0
%        subs(l2...ln), n > 0


subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T],R):-
    subs(T,R).


%sum of elements
%suma(l1l2...ln, c) =
%      c, n = 0
%      suma(l2...ln, l1 + c), otherwise

suma([], C, C).
suma([H|T], C, R) :-
    NC is C + H,
    suma(T, NC, R).

% check if sum of elements is divisible by N
% check(l1l2...ln, N) =
%       true, suma(l1l2...ln,0) mod N == 0
%       false, otherwise

check(L,N):-
    suma(L,0,R),
    R mod N =:= 0.

%one solution for the problem
%onesol(L:list, N:number, R:list)
%onesol(i,i,o)

onesol(L,N,R):-
    subs(L,R),
    check(R,N).

%all solutions for the problem
%allsols(L:list, N:number, R:list)
%allsols(i,i,o)

allsols(L,N,R):-
    findall(RL, onesol(L, N, RL), R).
