import os
import re

for root, dirs, files in os.walk('src/main/java/com/intellect/financeTracker'):
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                content = f.read()

            new_content = re.sub(r'\bLong userId\b', 'Integer userId', content)
            new_content = re.sub(r'JpaRepository<User,\s*Long>', 'JpaRepository<User, Integer>', new_content)
            
            # Specifically for EmployeeService.java
            new_content = new_content.replace('Long.valueOf(adminId)', 'adminId')
            
            # Other potential Long parsing issues for User ID, e.g., in CustomUserDetailsService
            if new_content != content:
                print(f'Updated {filepath}')
                with open(filepath, 'w') as f:
                    f.write(new_content)
