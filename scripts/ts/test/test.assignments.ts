import { type float, double, int, long } from "./test.type.aliases.ts";

class Test {
  // public sum(n: int): int {
  //   let sum: int = 0;
  //   for (let i: int = 0; i < n; i++) {
  //     sum += i;
  //   }
  //   return sum;
  // }

  public assignAndCast(a: int, b: long): double {
    let c: long = a;
    let d: long = b;
    return c + d;
  }
}

// console.log(new Test().sum(10));
console.log(new Test().assignAndCast(1, 2));