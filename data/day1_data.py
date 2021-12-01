import os

aoc_day_num = 1

local_file = os.path.join(os.path.dirname(__file__), f'day{aoc_day_num}.txt')

sample_depth_input = [
    199,
    200,
    208,
    210,
    200,
    207,
    240,
    269,
    260,
    263
]

with open(local_file, 'r') as inp:
    lines = inp.read().splitlines()

depth_input = [int(x) for x in lines]
