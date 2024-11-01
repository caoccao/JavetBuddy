import { type float, double, int, long } from "./test.type.aliases.ts";

class Test {
  public logicalGE_II_Z(a: int, b: int): boolean {
    return a >= b;
  }

  public logicalGE_IL_Z(a: int, b: long): boolean {
    const c: boolean = a >= b;
    return c;
  }

  public logicalGT_II_Z(a: int, b: int): boolean {
    return a > b;
  }

  public logicalGT_IL_Z(a: int, b: long): boolean {
    return a > b;
  }

  public logicalLE_II_Z(a: int, b: int): boolean {
    return a <= b;
  }

  public logicalLE_IL_Z(a: int, b: long): boolean {
    return a <= b;
  }

  public logicalLT_II_Z(a: int, b: int): boolean {
    return a < b;
  }

  public logicalLT_IL_Z(a: int, b: long): boolean {
    return a < b;
  }
}

console.log(new Test().logicalGE_II_Z(1, 2));
console.log(new Test().logicalGE_IL_Z(1, 2));
console.log(new Test().logicalGT_II_Z(1, 2));
console.log(new Test().logicalGT_IL_Z(1, 2));
console.log(new Test().logicalLE_II_Z(1, 2));
console.log(new Test().logicalLE_IL_Z(1, 2));
console.log(new Test().logicalLT_II_Z(1, 2));
console.log(new Test().logicalLT_IL_Z(1, 2));
