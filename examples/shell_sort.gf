function shellSort(int array[], int size) int[]
begin
    var int i;
    var int gap;
    gap = size / 2;

    while(gap > 0)
    begin
        repeat i in (gap, size, 1)
        begin
            var int temp;
            var int j;
            j = 1;
            temp = array[i];

            while(j >= gap and array[j - gap] > temp)
            begin
                array[j] = array[j - gap];
                j = j - temp;
            end

            array[j] = temp;
        end

        gap = gap / 2;
     end

     return 0;
end

function printArray(int array[], int size) int
begin
    var int i;

    repeat i in (1, size, 1)
    begin
        print(array[i] ++ " ");
    end
    print("\n");

    return 0;
end

function readArray(int size) int[]
begin
    var int array[size];
    var int i;

    repeat i in (1, size, 1)
    begin
        read(array[i]);
    end

    return array;
end

main() int
begin
    var int unsorted[10];
    printArray(readArray(10));
    printArray(shellSort(unsorted, 10));

    return 0;
end