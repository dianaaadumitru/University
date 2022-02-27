% a. Write a predicate to determine the sum of two numbers written in list representation


% Model matematic:
% len(l1...ln) =
%	0, n = 0
%	1 + len(l2...ln), otherwise

% len(L:list, R:number)
% flow model: len(i, o)

len([], 0).
len([_|T], R) :-
    len(T, RC),
    R is RC + 1.


% Model matematic:
% append2(l1...ln, e) =
%	[e], n = 0
%	l1 + append2(l2...ln), otherwise

% append2(L:list, E:number, R:list)
% flow model: append2(i, i, o)

append2([], E, [E]).
append2([H|T], E, [H|R]) :-
    append2(T, E, R).


% Model matematic:
% inv_list(l1...ln) =
%	[], n = 0
%	my_append(inv_list(l2...ln), l1), otherwise

% inv_list(L:list, R:list)
% flow model: inv_list(i, o)


inv_list([], []).
inv_list([H|T], R) :-
    inv_list(T, RI),
    append2(RI, H, R).

%Model matematic
%sum2(a1...an, b1...bm, c) =
%      [], n = 0, m = 0, c = 0
%      [1], n = 0, m = 0, c = 1
%      (b1 + c) + sum2([], b2...bm, 0), b1 + c < 10
%      ((b1 + c) % 10) + sum2([], b2...bm, 0), b1 + c >= 10
%      (a1 + b1 + c) + sum2(a2...an, b2...bm, 0), a1 + b1 + c1 < 10
%      ((a1 + b1 + c) % 10) + sum2(a2...an, b2...bm, 0),a1 +b1 + c1 >=10

sum2([],[],0,[]).
sum2([],[],1,[1]).
sum2([],[B|TB],C,[BC|R]):-
    BC is (B+C) mod 10,
    BC - B - C =:=0, %no carry
    sum2([],TB,0,R).
sum2([],[B|TB],C,[BC|R]):-
    BC is (B+C) mod 10,
    BC - B - C =\= 0, %carry
    sum2([],TB,1,R).
sum2([A|TA],[B|TB],C,[ABC|R]):-
    ABC is (A+B+C) mod 10,
    ABC - A - B - C =:= 0, %no carry
    sum2(TA,TB,0,R).
sum2([A|TA],[B|TB],C,[ABC|R]):-
    ABC is (A+B+C) mod 10,
    ABC - A - B - C =\= 0, %carry
    sum2(TA,TB,1,R).


% Model matematic:
% sum_lists(a1...an, b1...bm) =
%	a1...an, m = 0
%	b1...bm, n = 0
%	inv_list(suma(inv_list(a1...an), inv_list(b1...bm), 0)), n <= m
%	inv_list(suma(inv_list(b1...bm), inv_list(a1...an), 0)), otherwise

% sum_lists(A:list, B:list, R:list)
% flow model: sum_lists(i, i, o)

sum_lists(A, [], A).
sum_lists([], B, B).
sum_lists(A, B, R) :-
    len(A, LA),
    len(B, LB),
    LA=<LB, !,
    inv_list(A, RA),
    inv_list(B, RB),
    sum2(RA, RB, 0, RS),
    inv_list(RS, R).
sum_lists(A, B, R) :-
    inv_list(A, RA),
    inv_list(B, RB),
    sum2(RB, RA, 0, RS),
    inv_list(RS, R).


%sum_lists([],[],R). - empty lists
%sum_lists([1,2,4],[],R). - second list empty
%sum_lists([],[3,5,1],R). - first list empty
%sum_lists([1,2,4],[3,5,1],R). - no carry
%sum_lists([1,2,9],[3,8,6],R). - with carry
%sum_lists([3,7,5],[5,8,2,1,9],R). - len(a) <= len(b)
%sum_lists([5,8,2,1,9],[3,7,5],R). - len(a) > len(b)



% b. For a heterogeneous list, formed from integer numbers and list of
% digits, write a predicate to compute the sum of all numbers
% represented as sublists. Eg.: [1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1,
% 2, 0], 6] => [8, 2, 2].


% Model matematic:
% heter_list(l1...ln) =
%      [], n = 0
%      sum_lists(l1, heter_list(l2...ln), is_list(l1) = True
%      heter_list(l2...ln), otherwise


heter_list([], []).
heter_list([H|T], R) :- is_list(H),
    heter_list(T, RH),
    sum_lists(H, RH, R).
heter_list([_|T], R) :-
    heter_list(T, R).

% heter_list([1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] ,R).
% heter_list([1],R).
% heter_list([],R).
















