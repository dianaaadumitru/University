import csv

from solver import runSolver


def read_data_from_csv(file_name, first_value, second_value, output_label):
    data = []
    with open(file_name) as csv_file:
        rows = list(csv.reader(csv_file))

        data_names = rows[0]
        for row in rows[1:]:
            data.append(row)

    i = []
    o = []
    for row in data:
        i.append((
            float(row[data_names.index(first_value)]),
            float(row[data_names.index(second_value)])
        ))
        o.append(str(row[data_names.index(output_label)]))

    return i, o


def main():
    i, o = read_data_from_csv("dataset.csv", "val1", "val2", "label")

    runSolver(i, o)


if __name__ == '__main__':
    main()
