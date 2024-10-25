function sum(n: int): int {
  let sum: int = 0;
  for (let i: int = 0; i < n; i++) {
    sum += i;
  }
  return sum;
}

console.log(sum(10));
