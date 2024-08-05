# INIT DATABASE (FILL SOME TABLES)

1. RUN `python manage.py shell`

2. IN INTERPRETER RUN THESE COMMANDS
```
from init_db import *
init()
```
   - there will be warrnings about datetime but that is ok
# CLEAN DATABASE (EMPTY ALL TABLES)
  - _RECOMENDED BEFORE EACH `init()`_
1. RUN `python manage.py shell`

2. IN INTERPRETER RUN THESE COMMANDS
```
from init_db import *
clean()
```
