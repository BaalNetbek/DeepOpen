from os.path import dirname, exists, getsize, abspath
import matplotlib.pyplot as plt
from datetime import datetime

class Logger:
    def __init__(self, log_path, chart_path, subject: str = ''):
        self.log_updated = False
        self.log_path = log_path
        self.chart_path = chart_path
        self.subject = subject

    def log_values(self, values):
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M")
        new_log_entry = f"{timestamp},{','.join(map(str, values))}\n"
        
        # Checks if log is up to date
        if exists(self.log_path) and getsize(self.log_path) > 0:
            with open(log_path, "r") as file:
                last_line = file.readlines()[-1]
            if float(new_log_entry.split(',')[1]) == float(last_line.split(',')[1]):
                return    
        # Updates log        
        with open(log_path, "a") as file:
            print(f"Appending new entry log: {abspath(self.log_path)}")
            self.log_updated = True
            file.write(new_log_entry)

    def plot_and_save(self):
        data = []
        with open(self.log_path, "r") as file:
            print(f"Reading log file: {abspath(self.log_path)}")
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
            plt.plot(timestamps, val_set, label=lables[i], marker = '', drawstyle='steps-post')

        plt.xlabel("Time")
        plt.ylabel("Progress")
        plt.title("Deobfuscating " + self.subject + " progress")
        plt.legend(loc='upper left')
        plt.ylim(0, 100)
        plt.yticks([i / 2 for i in range(0, 202, 25)], [f'{i/2}%' for i in range(0, 202, 25)])
        plt.xticks(rotation=45)
        plt.grid(True, linestyle='--', alpha=0.5)
        plt.tight_layout()
        print(f"Saving chart: {abspath(self.chart_path)}")
        plt.savefig(self.chart_path)
        plt.close()

if __name__ == "__main__":
    from gof2_progress import check_obfuscation_progerss
    log_path = dirname(__file__) + r"/progress.log"
    chart_path = dirname(__file__) + r"/progress_chart.png"
    file_path =  dirname(dirname(dirname(__file__))) + '/Recaf/GoF2/GoF2_JSR_1.0.4.mapping'
    subject = "Galaxy on Fire 2 v1.0.4 (M3G)"
    values = check_obfuscation_progerss(file_path)
    logger = Logger(log_path, chart_path, subject)
    logger.log_values(values)
    if logger.log_updated or not exists(chart_path):
        logger.plot_and_save()
