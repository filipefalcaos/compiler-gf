function fibonacci(int limit) int
begin
    var int count;
    var int fib1;
    var int fib2;
    var int fib3;

    count = 0;
    fib1 = fib2 = 1;

    if (limit == 0)
    begin
        print("0");
    end
    elif (limit >= 1)
    begin
        print("0,");
    end

    while (fib1 + fib2  <= limit)
    begin
        if (count < 2)
        begin
            print("1,");
        end
        else
        begin
            fib3 = fib2 + fib1;
            fib1 = fib2;
            fib2 = fib3;

            if (fib3 < limit)
            begin
                print(fib3 ++ ",");
            end
            elif (fib3 = limit)
            begin
                print(fib3);
            end
        end

        count = count + 1;
    end

    return 0;
end

main() int
begin
    var int limit;
    read(limit);
    fibonacci(limit);
    return 0;
end