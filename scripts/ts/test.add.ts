import { type int } from "./type.aliases";

class Test {
  public add(a: int, b: int): int {
    return a + b;
  }
}

console.log(new Test().add(1, 2));
