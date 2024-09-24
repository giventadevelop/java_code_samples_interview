    package com.boot2;

    import java.util.function.IntFunction;
    import java.util.stream.Collectors;
    import java.util.stream.IntStream;


    public class NumbersDivisibleFizzBuzz {

        public static String processFizzBuzzStream(IntStream intStream){

            IntFunction<String> buzzFn = (x) -> {

                if (x % 3 == 0 && x % 5 == 0){
                    return "FizzBuzz";
                }else  if (x % 3 == 0){
                    return "Fizz";
                }else  if (x % 5 == 0){
                    return "Buzz";                }
                return Integer.toString(x);
            };
            String processStream = intStream.mapToObj(x-> buzzFn.apply(x))
                                            .collect(Collectors.joining(" , "));
            System.out.println("\n processStream  "+ processStream);
            return processStream;
        }
        public static void main(String args[]) {

//            int c = checkExceptionsFinally(4,0) ;
//            System.out.println(c);
            System.out.println("Math Floor");
            System.out.println(Math.floor(-4.7));
            System.out.println("Math Ceil");
            System.out.println(Math.ceil(-4.7));
            System.out.println("Math Round");
            System.out.println(Math.round(-4.7));
//            System.out.println("Math Min");
//            System.out.println(Math.min(-4.7));
        }

        static int checkExceptionsFinally(int a, int b) {
            int c= -1;

            try{
                c= a/b;

            } catch (Exception e) {
                System.err.println("Exc");
            } finally {
                System.err.println("Final");
            }

            return c;
        }
    }
