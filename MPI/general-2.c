#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(int argc, char** argv) {
    int rank, size;
    int total_elements; // total elements in array
    int elements_per_process;
    int *data = NULL; // full array at root
    int *sub_data;    // partial data at each process

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get current process id
    MPI_Comm_size(MPI_COMM_WORLD, &size); // get total number of processes

    if (rank == 0) {
        printf("Enter total number of elements (should be divisible by number of processes %d): ", size);
        scanf("%d", &total_elements);

        if (total_elements % size != 0) {
            printf("Error: total_elements must be divisible by number of processes.\n");
            MPI_Abort(MPI_COMM_WORLD, 1);
            exit(1);
        }

        data = (int*)malloc(sizeof(int) * total_elements);

        // fill array with random numbers
        srand(time(NULL));
        printf("Original array:\n");
        for (int i = 0; i < total_elements; i++) {
            data[i] = rand() % 100;
            printf("%d ", data[i]);
        }
        printf("\n");
    }

    // Broadcast total_elements to all processes
    MPI_Bcast(&total_elements, 1, MPI_INT, 0, MPI_COMM_WORLD);

    elements_per_process = total_elements / size;
    sub_data = (int*)malloc(sizeof(int) * elements_per_process);

    // Scatter data to all processes
    MPI_Scatter(data, elements_per_process, MPI_INT, sub_data, elements_per_process, MPI_INT, 0, MPI_COMM_WORLD);

    // Each process operates on its sub_data
    printf("Process %d received data: ", rank);
    for (int i = 0; i < elements_per_process; i++) {
        printf("%d ", sub_data[i]);
    }
    printf("\n");

    // Example: Calculate local sum
    int local_sum = 0;
    for (int i = 0; i < elements_per_process; i++) {
        local_sum += sub_data[i];
    }
    printf("Process %d local sum: %d\n", rank, local_sum);

    // Now reduce to get final sum at root
    int final_sum = 0;
    MPI_Reduce(&local_sum, &final_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        printf("Final total sum: %d\n", final_sum);
    }

    // Free memory
    if (rank == 0) free(data);
    free(sub_data);

    MPI_Finalize();
    return 0;
}

// mpirun -np 4 ./general-2
// 12 <--- write in console when executed as "Enter" message is not visisble at the start
