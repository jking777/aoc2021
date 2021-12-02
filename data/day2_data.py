import os

aoc_day_num = 2

local_file = os.path.join(os.path.dirname(__file__), f'day{aoc_day_num}.txt')

sample_commands = [
    "forward 5",
    "down 5",
    "forward 8",
    "up 3",
    "down 8",
    "forward 2"
]

with open(local_file, 'r') as inp:
    commands = inp.read().splitlines()
