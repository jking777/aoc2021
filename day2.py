from data.day2_data import sample_commands, commands

#input = sample_commands
input = commands

h_position = 0
depth = 0

for command in input:
    tokens = command.split()
    arg_value = int(tokens[1])
    if tokens[0] == "forward":
        h_position += arg_value
    elif tokens[0] == "down":
        depth += arg_value
    elif tokens[0] == "up":
        depth -= arg_value
    else:
        raise ValueError(f"{tokens[0]} is an unknown command")

print(f"Part 1 final position:  Horizontal {h_position}  Depth {depth}    Answer {h_position * depth}")

h_position = 0
depth = 0
aim = 0

for command in input:
    tokens = command.split()
    arg_value = int(tokens[1])
    if tokens[0] == "forward":
        h_position += arg_value
        depth += aim * arg_value
    elif tokens[0] == "down":
        aim += arg_value
    elif tokens[0] == "up":
        aim -= arg_value
    else:
        raise ValueError(f"{tokens[0]} is an unknown command")

print(f"Part 2 final position:  Horizontal {h_position}  Depth {depth}    Answer {h_position * depth}")
