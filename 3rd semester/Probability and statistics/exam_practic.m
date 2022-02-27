pkg load statistics

%no 10
%a. At the 1% significance level, do the population variances seem to differ?
%test for 2 populations
alpha = 0.01

%h0: sigma1  = sigma2
%h1: sigma1 =! sigma2 - two-tailed test

X1 = [4.6,0.7,4.2,1.9,4.8,6.1,4.7,5.5,5.4] %sample test 1
x2 = [2.5,1.3,2.0,1.8,2.7,3.2,3.0,3.5,3.4] %sample test 2

%we have two vectors and have to check if the pupulation variances seem to differ sa we use vartest2
[h, p, ci, stats] = vartest2(X1,X2,'alpha',alpha,'tail','both');
%if h = 0, h is not rejected, otherwise it is; 
%p-P value; 
%ci = confidence level

fprintf('\n h is %d\n', h)
if h == 0
    fprintf('H0 is not rejected, sigmas are equal\n');
else 
    fprintf('H0 is rejected, population variances differ \n');
end


%b. Find a 99% confidence interval for the difference of the average heat loss

n1 = length(X1)
n2 = length(X2)

confLevel = 0.99
alpha = 1 - confLevel

s1 = var(X1)
s2 = var(x2)


%from a) => sigmas are equal and unknown so we use the second formula from 3.
a = (n1 - 1) *s1^2 + (n2 - 1) *s2^2
b = n1 + n2 - 2
sp = sqrt(a/b)

x1bar = mean(X1)
x2bar = mean(X2)

q = tinv(1 - alpha/2, n1 + n2 - 2) %quantiles refer to the T(n1 + n2 ? 2)

v1 = x1bar - x2bar - q * sp * sqrt(1/n1 + 1/n2)
v2 = x1bar - x2bar + q * sp * sqrt(1/n1 + 1/n2)

fprintf('Confidence interval for the differenceof the average heat losses is (%3.5f, %3.5f)\n', v1, v2)
