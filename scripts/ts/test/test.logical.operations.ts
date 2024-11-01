import {
  type byte,
  char,
  float,
  double,
  int,
  long,
  short,
} from "./test.type.aliases.ts";

class Test {
  public logicalEQEQ_DD_Z(a: double, b: double): boolean {
    return a === b;
  }

  public logicalEQEQ_FF_Z(a: float, b: float): boolean {
    return a === b;
  }

  public logicalEQEQ_II_Z(a: int, b: int): boolean {
    return a === b;
  }

  public logicalEQEQ_IL_Z(a: int, b: long): boolean {
    return a === b;
  }

  public logicalEQ_DD_Z(a: double, b: double): boolean {
    return a == b;
  }

  public logicalEQ_FF_Z(a: float, b: float): boolean {
    return a == b;
  }

  public logicalEQ_II_Z(a: int, b: int): boolean {
    return a == b;
  }

  public logicalEQ_IL_Z(a: int, b: long): boolean {
    return a == b;
  }

  public logicalGE_BB_Z(a: byte, b: byte): boolean {
    return a >= b;
  }

  public logicalGE_CC_Z(a: char, b: char): boolean {
    return a >= b;
  }

  public logicalGE_DD_Z(a: double, b: double): boolean {
    return a >= b;
  }

  public logicalGE_FF_Z(a: float, b: float): boolean {
    return a >= b;
  }

  public logicalGE_II_Z(a: int, b: int): boolean {
    return a >= b;
  }

  public logicalGE_IL_Z(a: int, b: long): boolean {
    const c: boolean = a >= b;
    return c;
  }

  public logicalGE_SS_Z(a: short, b: short): boolean {
    return a >= b;
  }

  public logicalGT_DD_Z(a: double, b: double): boolean {
    return a > b;
  }

  public logicalGT_FF_Z(a: float, b: float): boolean {
    return a > b;
  }

  public logicalGT_II_Z(a: int, b: int): boolean {
    return a > b;
  }

  public logicalGT_IL_Z(a: int, b: long): boolean {
    return a > b;
  }

  public logicalLE_DD_Z(a: double, b: double): boolean {
    return a <= b;
  }

  public logicalLE_FF_Z(a: float, b: float): boolean {
    return a <= b;
  }

  public logicalLE_II_Z(a: int, b: int): boolean {
    return a <= b;
  }

  public logicalLE_IL_Z(a: int, b: long): boolean {
    return a <= b;
  }

  public logicalLT_DD_Z(a: double, b: double): boolean {
    return a < b;
  }

  public logicalLT_FF_Z(a: float, b: float): boolean {
    return a < b;
  }

  public logicalLT_II_Z(a: int, b: int): boolean {
    return a < b;
  }

  public logicalLT_IL_Z(a: int, b: long): boolean {
    return a < b;
  }

  public logicalNotEQEQ_DD_Z(a: double, b: double): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_FF_Z(a: float, b: float): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_II_Z(a: int, b: int): boolean {
    return a !== b;
  }

  public logicalNotEQEQ_IL_Z(a: int, b: long): boolean {
    return a !== b;
  }

  public logicalNotEQ_DD_Z(a: double, b: double): boolean {
    return a != b;
  }

  public logicalNotEQ_FF_Z(a: float, b: float): boolean {
    return a != b;
  }

  public logicalNotEQ_II_Z(a: int, b: int): boolean {
    return a != b;
  }

  public logicalNotEQ_IL_Z(a: int, b: long): boolean {
    return a != b;
  }
}

console.log(new Test().logicalEQEQ_DD_Z(1, 2));
console.log(new Test().logicalEQEQ_FF_Z(1, 2));
console.log(new Test().logicalEQEQ_II_Z(1, 2));
console.log(new Test().logicalEQEQ_IL_Z(1, 2));
console.log(new Test().logicalEQ_DD_Z(1, 2));
console.log(new Test().logicalEQ_FF_Z(1, 2));
console.log(new Test().logicalEQ_II_Z(1, 2));
console.log(new Test().logicalEQ_IL_Z(1, 2));
console.log(new Test().logicalGE_BB_Z(1, 2));
console.log(new Test().logicalGE_CC_Z(1, 2));
console.log(new Test().logicalGE_DD_Z(1, 2));
console.log(new Test().logicalGE_FF_Z(1, 2));
console.log(new Test().logicalGE_II_Z(1, 2));
console.log(new Test().logicalGE_IL_Z(1, 2));
console.log(new Test().logicalGE_SS_Z(1, 2));
console.log(new Test().logicalGT_DD_Z(1, 2));
console.log(new Test().logicalGT_FF_Z(1, 2));
console.log(new Test().logicalGT_II_Z(1, 2));
console.log(new Test().logicalGT_IL_Z(1, 2));
console.log(new Test().logicalLE_DD_Z(1, 2));
console.log(new Test().logicalLE_FF_Z(1, 2));
console.log(new Test().logicalLE_II_Z(1, 2));
console.log(new Test().logicalLE_IL_Z(1, 2));
console.log(new Test().logicalLT_DD_Z(1, 2));
console.log(new Test().logicalLT_FF_Z(1, 2));
console.log(new Test().logicalLT_II_Z(1, 2));
console.log(new Test().logicalLT_IL_Z(1, 2));
console.log(new Test().logicalNotEQEQ_DD_Z(1, 2));
console.log(new Test().logicalNotEQEQ_FF_Z(1, 2));
console.log(new Test().logicalNotEQEQ_II_Z(1, 2));
console.log(new Test().logicalNotEQEQ_IL_Z(1, 2));
console.log(new Test().logicalNotEQ_DD_Z(1, 2));
console.log(new Test().logicalNotEQ_FF_Z(1, 2));
console.log(new Test().logicalNotEQ_II_Z(1, 2));
console.log(new Test().logicalNotEQ_IL_Z(1, 2));
