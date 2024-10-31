import { type float, double, int, long } from "./test.type.aliases.ts";

class Test {
  public logicalGT_II_Z(a: int, b: int): boolean {
    return a > b;
  }
}

console.log(new Test().logicalGT_II_Z(1, 2));
