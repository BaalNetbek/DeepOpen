from gof2_progress import check_obfuscation_progerss
from os.path import dirname, exists, getsize
import matplotlib.pyplot as plt
from datetime import datetime

log_path = "progress.log"
chart_path = "progress_chart.png"

def log_values(values):
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M")
    new_log_entry = f"{timestamp},{','.join(map(str, values))}\n"
    
    if exists(log_path) and getsize(log_path) > 0:
        with open(log_path, "r") as file:
            last_line = file.readlines()[-1]
        if float(new_log_entry.split(',')[1]) == float(last_line.split(',')[1]):
            return
    with open(log_path, "a") as file:
        file.write(new_log_entry)

def plot_and_save():
    data = []
    with open(log_path, "r") as file:
        for line in file:
            parts = line.strip().split(',')
            timestamp = datetime.strptime(parts[0], "%Y-%m-%d %H:%M")
            values = [float(v) for v in parts[1:]]
            data.append([timestamp] + values)

    timestamps = [entry[0] for entry in data]
    values = list(zip(*[entry[1:] for entry in data]))

    plt.figure(figsize=(8, 4), dpi=120)
    lables = ('Total','Classes','Methods','Fields')
    for i, val_set in enumerate(values):
        plt.plot(timestamps, val_set, label=lables[i], marker = 'o', drawstyle='steps-post')

    plt.xlabel("Time")
    plt.ylabel("Progress")
    plt.title("Deobfuscating names progress")
    plt.legend(loc='upper left')
    plt.ylim(0, 100)
    plt.yticks([i / 2 for i in range(0, 202, 25)], [f'{i/2}%' for i in range(0, 202, 25)])
    plt.xticks(rotation=45)
    plt.grid(True, linestyle='--', alpha=0.5)
    plt.tight_layout()
    plt.savefig(chart_path)
    plt.close()

if __name__ == "__main__":
    file_path =  dirname(dirname(dirname(__file__))) + '/Recaf/GoF2/GoF2_JSR_1.0.4.mapping'
    values = check_obfuscation_progerss(file_path)
    log_values(values)
    plot_and_save()
