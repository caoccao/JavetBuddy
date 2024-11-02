import { double, float, type int, long } from "./test.type.aliases.ts";

class Test {
  public add_DD_I(a: double, b: double): double {
    return a + b + (-1);
  }

  public add_FF_I(a: float, b: float): float {
    return a + b + (-1);
  }

  public add_II_I(a: int, b: int): int {
    return a + b;
  }

  public add_II_J(a: int, b: int): long {
    return a + b;
  }

  public add_IJ_J(a: int, b: long): long {
    return a + b;
  }

  public add_JI_J(a: long, b: int): long {
    return a + b;
  }

  public divide_II_I(a: int, b: int): int {
    return a / b;
  }

  public minus_II_I(a: int, b: int): int {
    return -(a + b);
  }

  public mod_II_I(a: int, b: int): int {
    return a % b;
  }

  public multiply_II_I(a: int, b: int): int {
    return a * b;
  }

  public pow_DD_D(a: double, b: double): double {
    return a ** b;
  }

  public pow_II_D(a: int, b: int): double {
    return a ** b;
  }

  public shiftLeft_II_I(a: int, b: int): int {
    return a << b;
  }

  public shiftRight_II_I(a: int, b: int): int {
    return a >> b;
  }

  public subtract_II_I(a: int, b: int): int {
    return a - b;
  }

  public zeroFillShiftRight_II_I(a: int, b: int): int {
    return a >>> b;
  }

  public zeroFillShiftRight_JJ_J(a: long, b: long): long {
    return a >>> b;
  }
}

console.log(new Test().add_DD_I(1, 2));
console.log(new Test().add_FF_I(1, 2));
console.log(new Test().add_II_I(1, 2));
console.log(new Test().add_II_J(1, 2));
console.log(new Test().add_IJ_J(1, 2));
console.log(new Test().add_JI_J(1, 2));
console.log(new Test().divide_II_I(3, 2));
console.log(new Test().minus_II_I(3, 2));
console.log(new Test().mod_II_I(3, 2));
console.log(new Test().multiply_II_I(3, 2));
console.log(new Test().pow_DD_D(3, 2));
console.log(new Test().pow_II_D(3, 2));
console.log(new Test().shiftLeft_II_I(3, 2));
console.log(new Test().shiftRight_II_I(3, 1));
console.log(new Test().subtract_II_I(3, 2));
console.log(new Test().zeroFillShiftRight_II_I(3, 1));
console.log(new Test().zeroFillShiftRight_JJ_J(3, 1));
