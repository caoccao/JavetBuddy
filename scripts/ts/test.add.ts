import { type int } from "./type.aliases";

class Test {
  public add_II_I(a: int, b: int): int {
    return a + b;
  }
}

console.log(new Test().add_II_I(1, 2));
