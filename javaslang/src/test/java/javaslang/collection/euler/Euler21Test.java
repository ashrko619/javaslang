/*     / \____  _    _  ____   ______  / \ ____  __    _______
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  //  /\__\   JΛVΛSLΛNG
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/ \ /__\ \   Copyright 2014-2016 Javaslang, http://javaslang.io
 * /___/\_/  \_/\____/\_/  \_/\__\/__/\__\_/  \_//  \__/\_____/   Licensed under the Apache License, Version 2.0
 */
package javaslang.collection.euler;

import javaslang.Function1;
import javaslang.collection.Stream;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <strong>Problem 21: Amicable numbers</strong>
 *
 * <p>Let d(<i>n</i>) be defined as the sum of proper divisors of <i>n</i> (numbers less than <i>n</i> which divide evenly into <i>n</i>).<br />
 * If d(<i>a</i>) = <i>b</i> and d(<i>b</i>) = <i>a</i>, where <i>a</i> ≠ <i>b</i>, then <i>a</i> and <i>b</i> are an amicable pair and each of <i>a</i> and <i>b</i> are called amicable numbers.</p>
 * <p>For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.</p>
 * <p>Evaluate the sum of all the amicable numbers under 10000</p>
 * See also <a href="https://projecteuler.net/problem=21">projecteuler.net problem 21</a>.
 */

public class Euler21Test {

    @Test
    public void shouldSolveProblem21() {
        assertThat(sumOfDivisors(220)).isEqualTo(284); //1 + 2 + 4 + 5 + 10 + 11 + 20 + 22 + 44 + 55 + 110
        assertThat(sumOfDivisors(284)).isEqualTo(220); //1 + 2 + 4 + 71 + 142
        assertThat(sumOfAmicablePairs(10000)).isEqualTo(31626);
    }

    private static boolean isPerfectSquare(int n) {
        final int sqrtN = (int) Math.sqrt(n);
        return sqrtN * sqrtN == n;
    }

    private static int sumOfDivisors(int n) {
        final int sqrtN = (int) Math.sqrt(n);
        return Stream.rangeClosed(1, sqrtN)
                .foldLeft(0, (sum, d) -> n % d == 0 ? sum + d + n / d : sum)
                - n - (isPerfectSquare(n) ? sqrtN : 0);
    }

    private static int sumOfAmicablePairs(int n) {
        Function1<Integer, Integer> msd = Function1.of(Euler21Test::sumOfDivisors).memoized();
        return Stream.range(1, n)
                .foldLeft(0, (sum, x) -> {
                    boolean isAmicable = (msd.apply(msd.apply(x)).intValue() == x
                            && msd.apply(x) > x);
                    return isAmicable ? sum + x + msd.apply(x) : sum;
                });
    }

}
