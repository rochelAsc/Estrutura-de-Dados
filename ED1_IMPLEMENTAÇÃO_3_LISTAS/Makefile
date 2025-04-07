FLAGS   = -O0 -g
SOURCE  = $(wildcard src/*.c)
BUILD   = build
INCLUDE = include

$(BUILD)/debug: $(SOURCE)
	$(CC) -I$(INCLUDE) $(FLAGS) -o $@ $^

all: build/debug