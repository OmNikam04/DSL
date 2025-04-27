#include<mpi.h>
#include<stdio.h>
#include<stdlib.h>

int main(int argc, char* argv[]){
    int rank, size;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    int *arr = NULL;
    if(rank == 0){
        arr = (int*)malloc(sizeof(int) * size);
        printf("Original array elements: \n");
        for(int i = 0; i<size; i++){
            arr[i] = rand() % 10 + 1;
            printf("%d ", arr[i]);
        }
        printf("\n");
    }
    int local_val;
    MPI_Scatter(arr, 1, MPI_INT,
                &local_val, 1, MPI_INT,
                0, MPI_COMM_WORLD);
    
    int *gathered_res = NULL;
    if(rank == 0){
        gathered_res = (int*)malloc(sizeof(int) * size);
    }
    int temp = local_val;
    MPI_Gather(&temp, 1, MPI_INT,
        gathered_res, 1, MPI_INT,
        0, MPI_COMM_WORLD);
    
    if(rank == 0){
        printf("Performing multiplication on gathered array: \n");
        int mul = 1; 
        for(int i = 0; i<size; i++){
            mul *= gathered_res[i];
        }
        printf("%d\n", mul);
    }

    MPI_Finalize();

    return 0;
}