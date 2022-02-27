pkg load statistics

x = [7, 7, 4, 5, 9, 9, 4, 12, 8, 1, 8, 7, 3, 13, 2, 1, 17, 7,
12, 5, 6, 2, 1, 13, 14, 10, 2, 4, 9, 11, 3, 5, 12, 6, 10, 7];
n = length(x);


#left tail test

alpha = 0.05;
M = 9;
sigma = 5;

[h,P,CI,ZVAL,_] = ztest(x,M,sigma,"alpha",alpha,"tail","left")



z2 = norminv(alpha);
RR = [-inf, z2];

    fprintf('\n h is %d', h)
if h == 1 % result of the test, h = 0, if H0 is NOT rejected, h = 1, if H0 IS rejected
    fprintf('\n So the null hypothesis is rejected,\n')
    fprintf('i.e. the data suggests that the standard IS NOT met.\n')
else
    fprintf('\n So the null hypothesis is not rejected,\n')
    fprintf('i.e. the data suggests that the standard IS  met.\n')
end   


fprintf('the rejection region is (%4.4f, %4.4f)\n', RR)
fprintf('the value of the test statistic z is %4.4f\n', ZVAL)
fprintf('the P-value of the test is %4.4f\n\n', P)

#[h,P,CI,ZVAL] = ztest(x, M, sigma, alpha, -1);