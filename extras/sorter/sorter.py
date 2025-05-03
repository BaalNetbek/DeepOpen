def custom_order_sort(order_file, mapping_file, output_file):
    # Load the files
    with open(order_file, 'r') as f:
        order_lines = [line.strip() for line in f.readlines()]
    
    with open(mapping_file, 'r') as f:
        mapping_lines = [line.strip() for line in f.readlines()]
    
    # Create mapping entries dictionary
    mapping_dict = {}
    for line in mapping_lines:
        parts = line.rsplit(' ', 1)
        if len(parts) == 2:
            key, value = parts
            mapping_dict[key] = line
    
    sorted_lines = []
    matched_keys = set()
    
    # lines in the order file order
    for key in order_lines:
        if key in mapping_dict:
            sorted_lines.append(mapping_dict[key])
            matched_keys.add(key)
    
    # remaining unmatched lines
    for line in mapping_lines:
        parts = line.rsplit(' ', 1)
        if len(parts) == 2:
            key = parts[0]
            if key not in matched_keys:
                sorted_lines.append(line)
    
    with open(output_file, 'w') as f:
        f.write('\n'.join(sorted_lines))

if __name__ == "__main__":
    from os.path import dirname
    order_file = dirname(__file__) + r'/GoF2_JSR_1.0.4_order.txt'
    mapping_file = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/GoF2_JSR_1.0.4_unordered.mapping'
    output_file = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/GoF2_JSR_1.0.4.mapping'
    custom_order_sort(order_file, mapping_file, output_file)
    print(f"Mapping sorted and saved to '{output_file}'.")