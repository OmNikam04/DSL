#include<mpi.h>
#include<stdio.h>
#include<stdlib.h>

int main(int argc, char* argv[]){
    int rank, size, local_value;
    int *arr;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if(rank == 0){
        arr = (int*)malloc(sizeof(int) * size);
        printf("Original array created with following elements\n");
        for(int i = 0; i<size; i++){
            arr[i] = rand() %10 + 1;
            printf("%d\t", arr[i]);
        }
        printf("\n");
    }

    MPI_Scatter(arr, 1, MPI_INT, 
                &local_value, 1, MPI_INT,
                0, MPI_COMM_WORLD);

    int local_result = local_value;

    int *gatherd_res = NULL;
    if(rank == 0){
        gatherd_res = (int*)malloc(sizeof(int) * size);
    }
    MPI_Gather(&local_result, 1, MPI_INT,
                gatherd_res, 1, MPI_INT,
                0, MPI_COMM_WORLD);

    // Final operation at root
    if(rank == 0){
        if(gatherd_res != NULL){
            printf("Intermediate results at root are \n");
            int final_sum = 0;
            for(int i = 0; i<size; i++){
                printf("%d\t", gatherd_res[i]);
                final_sum += gatherd_res[i];
            }
            printf("\n");
            printf("Total sum of all elements: %d\n", final_sum);
        }
    }

    MPI_Finalize();

    return 0;
}