%a. Find a 95% confidence interval for the average size of nickel particles

x = [3.26,1.89,2.42,2.03,3.07,2.95,1.39,3.06,2.46,3.35,1.56,1.79,1.76,3.82,2.42,2.96]

s = std(x);  % standard deviation of the sample
q2 = tinv(1 - alpha/2, n - 1);
b1 = xbar - (s/sqrt(n)) * q2;
b2 = xbar + (s/sqrt(n)) * q2;
fprintf('Confidence interval for population mean, miu, where sigma is unknown is (%3.5f, %3.5f)\n', b1, b2)


%b. At the 1% significance level, on average, do these nickel particles seem to be smaller than 3?
alpha = 0.01
n0 = 3

%to be smaller than 3 => tail = left
[h,p,ci,stats] = ttest(x,n0,'alpha', alpha, 'tail','left')

if h == 0
    fprintf('H0 is not rejected, i.e, average is smaller than 3\n');
else 
    fprintf('H0 is rejected, average is greather than 3 \n');
end
