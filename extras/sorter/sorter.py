import pandas as pd

def custom_order_sort(order_file, mapping_file, output_file):
    # Load the order and mapping files
    order = pd.read_csv(order_file, header=None, dtype=str)
    mapping = pd.read_csv(mapping_file, header=None, dtype=str)

    # Extract keys from order (concatenate columns into a single string for comparison)
    order_keys = order.fillna('').agg(' '.join, axis=1).tolist()

    # Extract keys from mapping, excluding the last word
    mapping_keys = mapping.fillna('').agg(' '.join, axis=1).str.rsplit(' ', n=1).str[0].tolist()

    # Create a dictionary
    mapping_rows = {key: row for key, row in zip(mapping_keys, mapping.values.tolist())}

    # Sort mapping rows based on order, keeping unmatched rows in original sequence
    sorted_rows = []
    unmatched_rows = []
    matched_keys = set()
    for key in order_keys:
        if key in mapping_rows:
            sorted_rows.append(mapping_rows[key])
            matched_keys.add(key)

    unmatched_rows = [row for key, row in zip(mapping_keys, mapping.values.tolist()) if key not in matched_keys]

    final_rows = sorted_rows + unmatched_rows
    sorted_mapping = pd.DataFrame(final_rows)
    sorted_mapping.to_csv(output_file, index=False, header=False)

if __name__ == "__main__":
    from os.path import dirname
    order_file = dirname(__file__) + r'/GoF2_JSR_1.0.4_order.txt'
    mapping_file = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/GoF2_JSR_1.0.4_unordered.mapping'
    output_file = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/GoF2_JSR_1.0.4.mapping'
    custom_order_sort(order_file, mapping_file, output_file)
    print(f"Mapping sorted and saved to '{output_file}'.")
