package numbers;

public enum Property {
    EVEN {
        public boolean test(long number) {
            return number % 2 == 0;
        }
    },
    ODD {
        public boolean test(long number) {
            return number % 2 == 1;
        }
    },
    SQUARE {
        public boolean test(long number) {
            double sqrt = Math.sqrt(number);
            return sqrt == Math.floor(sqrt);
        }
    },
    SUNNY {
        public boolean test(long number) {
            double sqrt = Math.sqrt(number + 1);
            return sqrt == Math.floor(sqrt);
        }
    },
    BUZZ {
        public boolean test(long number) {
            return number % 7 == 0 || number % 10 == 7;
        }
    },
    DUCK {
        public boolean test(long number) {
            return Long.toString(number).contains("0");
        }
    },
    PALINDROMIC {
        public boolean test(long number) {
            String numberString = Long.toString(number);
            String reversedString = new StringBuilder(numberString).reverse().toString();
            return numberString.equals(reversedString);
        }
    },
    GAPFUL {
        public boolean test(long number) {
            if (number < 100) {
                return false;
            } else {
                long lastDigit = number % 10;
                long firstDigit = number;
                while (firstDigit >= 10) {
                    firstDigit /= 10;
                }
                return number % (firstDigit * 10 + lastDigit) == 0;
            }
        }
    },
    SPY {
        public boolean test(long number) {
            long sum = 0;
            long product = 1;

            while (number > 0) {
                long digit = number % 10;
                sum += digit;
                product *= digit;
                number /= 10;
            }

            return sum == product;
        }
    },
    JUMPING{
        public boolean test(long number) {
            if (number <= 9){
                return true;
            } else {
                String[] digits = Long.toString(number).split("");
                for (int i = 0; i < digits.length - 1; i++) {
                    if (Math.abs(Integer.parseInt(digits[i]) - Integer.parseInt(digits[i+1])) != 1) {
                        return false;
                    }
                }

            }
            return true;
        }
    },
    HAPPY {
        public boolean test(long number) {
            while (number != 1 && number != 4) {
                number = sumOfSquareOfDigits(number);
            }
            return number == 1;
        }

        private long sumOfSquareOfDigits(long num) {
            long sum = 0;
            while (num > 0) {
                long digit = num % 10;
                sum += digit * digit;
                num /= 10;
            }
            return sum;
        }
    },
    SAD {
        public boolean test(long number) {
            return !HAPPY.test(number);
        }
    };

    public static boolean isMutuallyExclusive(Property property1, Property property2) {
        return switch (property1) {
            case EVEN -> property2 == ODD || property2 == EVEN;
            case ODD -> property2 == EVEN;
            case SUNNY -> property2 == SQUARE;
            case SQUARE -> property2 == SUNNY;
            case SPY -> property2 == DUCK;
            case DUCK -> property2 == SPY;
            case HAPPY -> property2 == SAD;
            case SAD -> property2 == HAPPY;
            default -> false;
        };

    }

    public static boolean isNegativeMutuallyExclusive(Property property1, Property property2) {
        return switch (property1) {
            case EVEN -> property2 == ODD || property2 == EVEN;
            case ODD -> property2 == EVEN;
            case HAPPY -> property2 == SAD;
            case SAD -> property2 == HAPPY;
            default -> false;
        };

    }

    public abstract boolean test(long number);

}
