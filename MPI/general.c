// How to run?
// brew install open-mpi
// mpicc your_program.c -o your_program
// mpirun -np 4 ./distributed_array

// int MPI_Scatter(
//     const void *sendbuf,  // data to send (array)
//     int sendcount,        // number of elements to send to each process
//     MPI_Datatype sendtype,// type of data (e.g., MPI_INT)
//     void *recvbuf,        // buffer where each process stores its received data
//     int recvcount,        // number of elements each process receives
//     MPI_Datatype recvtype,// type of received data (e.g., MPI_INT)
//     int root,             // rank of root process (usually 0)
//     MPI_Comm comm         // communicator (usually MPI_COMM_WORLD)
// );


// int MPI_Gather(
//     const void *sendbuf,  // data each process is sending
//     int sendcount,        // number of elements to send
//     MPI_Datatype sendtype,// type of send data (e.g., MPI_INT)
//     void *recvbuf,        // buffer where root will collect everything
//     int recvcount,        // number of elements root expects from each
//     MPI_Datatype recvtype,// type of received data (e.g., MPI_INT)
//     int root,             // rank of root process (usually 0)
//     MPI_Comm comm         // communicator (usually MPI_COMM_WORLD)
// );

#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {
    int world_rank, world_size;
    int *array = NULL;
    int local_value;
    int final_result;

    MPI_Init(&argc, &argv);                         // Initialize MPI
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);      // Get process rank
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);      // Get total number of processes

    if (world_rank == 0) {
        // Root process initializes array
        array = (int*)malloc(sizeof(int) * world_size);
        printf("Original Array: ");
        for (int i = 0; i < world_size; i++) {
            array[i] = rand() % 10 + 1; // Random numbers between 1 and 10
            printf("%d ", array[i]);
        }
        printf("\n");
    }

    // Scatter one element to each process
    MPI_Scatter(array, 1, MPI_INT, &local_value, 1, MPI_INT, 0, MPI_COMM_WORLD);

    printf("Process %d received %d\n", world_rank, local_value);

    // Perform computation based on the operation
    // ********************
    // Change here based on problem:
    // ********************
    
    // 1. Sum
    int local_result = local_value;

    // 2. Multiplication
    // int local_result = local_value;

    // 3. Average
    // int local_result = local_value;

    // 4. Reciprocal
    // double local_result = 1.0 / local_value;

    // Gather results back to root
    int *gathered_results = NULL;
    if (world_rank == 0) {
        gathered_results = (int*)malloc(sizeof(int) * world_size);
    }

    MPI_Gather(&local_result, 1, MPI_INT, gathered_results, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // For Reciprocal (since it's double), gather separately
    // MPI_Gather(&local_result, 1, MPI_DOUBLE, gathered_results, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);

    // Final operation at root
    if (world_rank == 0) {
        if (gathered_results != NULL) {
            printf("\nIntermediate Results gathered at Root:\n");
            for (int i = 0; i < world_size; i++) {
                printf("%d ", gathered_results[i]);
            }
            printf("\n");

            // Final Computation based on the problem
            // *********************
            // Sum
            int total_sum = 0;
            for (int i = 0; i < world_size; i++) {
                total_sum += gathered_results[i];
            }
            printf("Total Sum: %d\n", total_sum);

            // Multiplication
            /*
            int total_product = 1;
            for (int i = 0; i < world_size; i++) {
                total_product *= gathered_results[i];
            }
            printf("Total Product: %d\n", total_product);
            */

            // Average
            /*
            int total_sum = 0;
            for (int i = 0; i < world_size; i++) {
                total_sum += gathered_results[i];
            }
            double avg = (double)total_sum / world_size;
            printf("Overall Average: %lf\n", avg);
            */

            // Reciprocal
            /*
            printf("Reciprocal Array:\n");
            for (int i = 0; i < world_size; i++) {
                printf("%lf ", (double)1 / gathered_results[i]);
            }
            printf("\n");
            */
        }
        free(array);
        free(gathered_results);
    }

    MPI_Finalize();  // Finalize MPI
    return 0;
}
