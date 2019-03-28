function fibonacci(int limit) empty
begin
    var int count;
    var int fib1;
    var int fib2;
    var int fib3;

    fib1 = fib2 = 1;

    if (limit == 0)
    begin
        print("0 ");
    end

    while (count < limit)
    begin
        if (count < 2)
        begin
            print("1 ");
        end
        else
        begin
            fib3 = fib2 + fib1;
            fib1 = fib2;
            fib2 = fib3;
            print(fib3 ++ " ");
        end

        count = count + 1
    end
end

main() empty
begin
    var int limit;
    read(int, limit);
    fibonacci(limit);
end