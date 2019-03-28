function shellSort(int array[], int size) int[]
begin
    var int i;
    var int j;
    var int gap;

    repeat (gap = size / 2; gap > 0; gap = gap / 2)
    begin
        repeat (i = gap; i < size; i = i + 1)
        begin
            int temp = array[i]
            int j;

            repeat (j = i; (j >= gap) and (array[j - gap] > temp); j = j - temp)
            begin
                array[j] = array[j - gap];
            end

            array[j] = temp;
        end
    end

    return 0;
end

main() empty
begin
    var int unsorted[10];
    var int sorted[10];
    unsorted = {9, 8, 3, 2, 5, 1, 4, 7, 6, 0}
    sorted = shellSort(unsorted, 10);
end
