def custom_order_sort(order_file, mapping_file, output_file, dont_duplicate=False):
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

    write_data = '\n'.join(sorted_lines)            
    if dont_duplicate:
        with open(mapping_file, 'r') as f:
            read_data = f.read()
        if write_data == read_data:
            
            return "no_changes"
    with open(output_file, 'w') as f:
        f.write('\n'.join(sorted_lines))

if __name__ == "__main__":
    from os.path import dirname
    import os
    def sort_for_do(name):
        """sort for DeepOpen - works DO repo directory tree"""
        try:
            game, version = name.split('_', 1)
            order_file = dirname(__file__) + f'/{name}_order.txt'
            mapping_file = dirname(dirname(dirname(__file__))) + f'/Recaf/{game}/{version}/{name}_unordered.mapping'
            output_file = dirname(dirname(dirname(__file__))) + f'/Recaf/{game}/{version}/{name}.mapping'
            no_unordered = False
            if not os.path.exists(mapping_file):
                no_unordered = True
                mapping_file = output_file
                output_file = dirname(dirname(dirname(__file__))) + f'/Recaf/{game}/{version}/{name}_sorted.mapping'
            result = custom_order_sort(order_file, mapping_file, output_file, no_unordered)
            if result == "no_changes":
                print(f"{name}: is already sorted. No write performed. ({mapping_file})")
            else:
                print(f"{name} mapping sorted and saved to '{output_file}'.")
        except FileNotFoundError as e:
            print(f"Error in sorting {name}: {e}")
    
    sort_for_do('GoF2_JSR_1.0.4')
    sort_for_do('GoF2_V3_1.0.4')