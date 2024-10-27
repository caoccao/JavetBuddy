import { type int, type long } from "./type.aliases";

class Test {
  public add_II_I(a: int, b: int): int {
    return a + b;
  }
  // public add_IL_L(a: int, b: long): long {
  //   return a + b;
  // }
}

console.log(new Test().add_II_I(1, 2));
// console.log(new Test().add_IL_L(1, 2));
