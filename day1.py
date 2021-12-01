from data.day1_data import depth_input, sample_depth_input

#input = sample_depth_input
input = depth_input

num_increases = 0
for i in range(1, len(input)):
    if input[i] > input[i-1]:
        num_increases += 1

print(f"Part 1 num depth increases: {num_increases}")

window_sums = []
for i in range(2, len(input)):
    window_sums.append(input[i] + input[i-1] + input[i-2])
num_increases = 0
for i in range(1, len(window_sums)):
    if window_sums[i] > window_sums[i-1]:
        num_increases += 1

print(f"Part 2 num window increases: {num_increases}")
